package com.example.covid_19.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.covid_19.DashboardActivity;
import com.example.covid_19.DataHandler;
import com.example.covid_19.R;
import com.example.covid_19.SqliDatabase;
import com.example.covid_19.databinding.FragmentHomeBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView confirmed = binding.confirmed;
        final TextView recovered = binding.recovered;
        final TextView active_cases = binding.activeCases;
        final TextView deceased = binding.deceased;
        SqliDatabase sql = new SqliDatabase(getActivity().getApplicationContext());
        DataHandler dashboard = sql.dashboard();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] s) {
                confirmed.setText(NumberFormat.getInstance(Locale.US).format(dashboard.getConfirmed()));
                recovered.setText(NumberFormat.getInstance(Locale.US).format(dashboard.getRecovered()));
                active_cases.setText(NumberFormat.getInstance(Locale.US).format(dashboard.getTotal_active()));
                deceased.setText(NumberFormat.getInstance(Locale.US).format(dashboard.getDeceased()));
            }
        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}