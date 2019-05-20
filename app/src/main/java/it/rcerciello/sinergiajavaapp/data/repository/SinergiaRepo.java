package it.rcerciello.sinergiajavaapp.data.repository;

import android.support.annotation.NonNull;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import it.rcerciello.sinergiajavaapp.data.managers.StaffModelResponse;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.APICallback;
import timber.log.Timber;

public class SinergiaRepo {

    private static SinergiaRepo ourInstance;


    public static synchronized SinergiaRepo getInstance() {
        if (ourInstance == null) {
            ourInstance = new SinergiaRepo();
        }
        return ourInstance;
    }


    public void insertServices(final List<ServiceModel> serviceItems) {

        deleteServices(new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                try (Realm realm = Realm.getDefaultInstance()) {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(@NonNull final Realm realm) {
                            realm.copyToRealmOrUpdate(serviceItems);

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Timber.e("services");
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(@NonNull Throwable error) {
                            Timber.e(error);
                        }
                    });
                } catch (Throwable t) {
                    Timber.e(t.getLocalizedMessage());
                }


            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onSessionExpired() {

            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });


    }


    public void deleteClients(APICallback<Boolean> mCallback) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.delete(ClientModel.class);
            realm.commitTransaction();
            mCallback.onSuccess(true);
        } catch (Exception e) {
            mCallback.onSuccess(false);
        }
    }


    public void deleteServices(APICallback<Boolean> mCallback) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.delete(ServiceModel.class);
            realm.commitTransaction();
            mCallback.onSuccess(true);
        } catch (Exception e) {
            mCallback.onSuccess(false);
        }
    }


    public void selectServices(final APICallback<List<ServiceModel>> mCallback) {
        try (Realm realm = Realm.getDefaultInstance()) {
            final List<ServiceModel> query = realm.where(ServiceModel.class).findAll();
            if (query != null) {
                mCallback.onSuccess(realm.copyFromRealm(query));
            } else {
                mCallback.onSuccess(null);
            }
        } catch (Throwable t) {
            Timber.e(t.getMessage());
        }
    }

    public void insertClients(final List<ClientModel> clientItems) {
        deleteClients(new APICallback<Boolean>() {
            @Override
            public void onSuccess(Boolean object) {
                try (Realm realm = Realm.getDefaultInstance()) {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(@NonNull Realm realm) {

                            realm.copyToRealmOrUpdate(clientItems);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Timber.e("insert client success");
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(@NonNull Throwable error) {
                            Timber.e(error);
                        }
                    });
                } catch (Throwable t) {
                    Timber.e(t.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onSessionExpired() {

            }

            @Override
            public void onFailure(boolean isFailure) {

            }
        });

    }


    public void selectClients(final APICallback<List<ClientModel>> mCallback) {
        try (Realm realm = Realm.getDefaultInstance()) {
            final List<ClientModel> query = realm.where(ClientModel.class).findAll();
            if (query != null) {
                mCallback.onSuccess(realm.copyFromRealm(query));
            } else {
                mCallback.onSuccess(null);
            }
        } catch (Throwable t) {
            Timber.e(t.getMessage());
        }
    }


    public void selectClientById(@NonNull String id, final APICallback<ClientModel> mCallback) {
        try (Realm realm = Realm.getDefaultInstance()) {
            final ClientModel query = realm.where(ClientModel.class).equalTo("primaryKeyModel.primaryKey", id).findFirst();
            if (query != null) {
                mCallback.onSuccess(realm.copyFromRealm(query));
            } else {
                mCallback.onSuccess(null);
            }
        } catch (Throwable t) {
            mCallback.onFailure(t.getLocalizedMessage());
            Timber.e(t.getMessage());
        }
    }




    public void selectServiceById(@NonNull String id, final APICallback<ServiceModel> mCallback) {
        try (Realm realm = Realm.getDefaultInstance()) {
            final ServiceModel query = realm.where(ServiceModel.class).equalTo("primaryKeyModel.primaryKey", id).findFirst();
            if (query != null) {
                mCallback.onSuccess(realm.copyFromRealm(query));
            } else {
                mCallback.onSuccess(null);
            }
        } catch (Throwable t) {
            Timber.e(t.getMessage());
        }
    }



    public void selectServicesByIds(@NonNull List<String> ids, final APICallback<List<ServiceModel>> mCallback) {

//        RealmQuery<Article> query = realm.where(Article.class);
//        if (articleIds.size() == 0) {
//            // We want to return an empty list if the list of ids is empty.
//            // Just search for an id that does not exist.
//            query = query.equalTo("id", -30000);
//        } else {
//            int i = 0;
//            for (int id : articleIds) {
//                // The or() operator requires left hand and right hand elements.
//                // If articleIds had only one element then it would crash with
//                // "Missing right-hand side of OR"
//                if (i++ > 0) {
//                    query = query.or();
//                }
//                query = query.equalTo("id", id);
//            }
//        }
//        return query.findAll();

        String[] allId = new String[ids.size()];
        int i = 0;
        for(String item : ids)
        {
            allId[i]= item;
            i++;
        }


        try (Realm realm = Realm.getDefaultInstance()) {
//            equalTo("primaryKeyModel.primaryKey", id).findFirst();
            RealmResults<ServiceModel> query = realm.where(ServiceModel.class).in("primaryKeyModel.primaryKey", allId).findAllSorted("serviceIdentifier", Sort.ASCENDING);;
           
            if (query!=null)
            {
                mCallback.onSuccess(realm.copyFromRealm(query));
            }else
            {
                mCallback.onSuccess(null);
            }

//            if (query != null) {
//                mCallback.onSuccess(realm.copyFromRealm(query));
//            } else {
//                mCallback.onSuccess(null);
//            }
        } catch (Throwable t) {
            Timber.e(t.getMessage());
        }
    }


    public void saveStaff(StaffModelResponse object, final APICallback<Boolean> mCallback) {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.delete(StaffModelResponse.class);
            realm.insertOrUpdate(object);
            realm.commitTransaction();
            mCallback.onSuccess(true);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
            mCallback.onSuccess(false);
        }


    }
}
