
package it.rcerciello.sinergiajavaapp.scene;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void logout();
}
