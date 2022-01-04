package com.example.covid_19.ui.gallery;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid_19.SqliDatabase;
import com.example.covid_19.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    private SqliDatabase sql;
    private String[][] results;
    List<String> mun;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final EditText query = binding.searchquery;
        final ListView list = binding.municipal;
        final ConstraintLayout data = binding.data;
        final TextView region = binding.region;
        final TextView province = binding.province;
        final TextView municipal = binding.municipalName;
        final TextView date = binding.date;
        final TextView active = binding.active;
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                data.setVisibility(View.GONE);
                if (query.getText().length() > 0){
                    sql = new SqliDatabase(getActivity().getApplicationContext());
                    results = sql.search(query.getText().toString());
                    mun = new ArrayList<>();
                    for (int i = 0; i < results.length; i++) {
                        mun.add(results[i][3]+", "+results[i][2]);
//                        System.out.println(results[i][3]+", "+results[i][2]);
                    }
                    ArrayAdapter li = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,mun);
                    list.setAdapter(li);
                }
                else{
                    list.setAdapter(null);
                }
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list.setAdapter(null);
                query.setText(null);
                data.setVisibility(View.VISIBLE);
                region.setText("Region: "+results[i][1]);
                province.setText("Province: "+results[i][2]);
                municipal.setText("Municipal: "+results[i][3]);
                date.setText("Last Updated: "+results[i][4]);
                active.setText("Active Cases: "+results[i][5]);
            }
        });
//        final TextView textView = binding.textGallery;
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
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