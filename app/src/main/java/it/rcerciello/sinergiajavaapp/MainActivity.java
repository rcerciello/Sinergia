package it.rcerciello.sinergiajavaapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.rcerciello.sinergiajavaapp.data.modelli.ClientModel;
import it.rcerciello.sinergiajavaapp.data.modelli.EmployeeModel;
import it.rcerciello.sinergiajavaapp.data.modelli.ServiceModel;
import it.rcerciello.sinergiajavaapp.data.network.ApiClient;
import it.rcerciello.sinergiajavaapp.scene.clients.detail.ClientDetailsActivity;
import it.rcerciello.sinergiajavaapp.scene.clients.list.ClientItemFragment;
import it.rcerciello.sinergiajavaapp.scene.employee.clients.detail.EmployeeDetailsActivity;
import it.rcerciello.sinergiajavaapp.scene.employee.clients.list.EmployeeFragment;
import it.rcerciello.sinergiajavaapp.scene.services.detail.ServiceDetailsActivity;
import it.rcerciello.sinergiajavaapp.scene.services.list.ServiceItemFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ClientItemFragment.OnClientInteractionListener,EmployeeFragment.OnEmployeeInteractionListener, ServiceItemFragment.OnServiceInteractionListener {

    @BindView(R.id.fragment)
    FrameLayout fragment;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    CalendarFragment fragment = CalendarFragment.newInstance(1);
//                    if (fragment != null) {
//                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.fragment, fragment);
//                        ft.commit();
//                    }
                    return true;
                case R.id.navigation_clients:
                    ClientItemFragment fragmentClient = ClientItemFragment.newInstance(1);
                    if (fragmentClient != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, fragmentClient);
                        ft.commit();
                    }
                    return true;
                case R.id.navigation_employee:
                    EmployeeFragment fragmentEmployee = EmployeeFragment.newInstance(1);
                    if (fragmentEmployee != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, fragmentEmployee);
                        ft.commit();
                    }
                    return true;
                case R.id.navigation_services:
                    ServiceItemFragment fragmentService = ServiceItemFragment.newInstance(1);
                    if (fragmentService != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, fragmentService);
                        ft.commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClientInteraction(ClientModel item) {
        Intent i = new Intent(this, ClientDetailsActivity.class);
         String data = ApiClient.getGson().toJson(item);
        i.putExtra("ClientModel", data);
        startActivity(i);
    }

    @Override
    public void onServiceInteraction(ServiceModel item) {
        Intent i = new Intent(this, ServiceDetailsActivity.class);
        String data = ApiClient.getGson().toJson(item);
        i.putExtra("ServiceModel", data);
        startActivity(i);
    }

    @Override
    public void onEmployeeInteraction(EmployeeModel item) {
        Intent i = new Intent(this, EmployeeDetailsActivity.class);
        String data = ApiClient.getGson().toJson(item);
        i.putExtra("ServiceModel", data);
        startActivity(i);
    }
}
