package com.example.calculate;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculate.databinding.FragmentFailedBinding;
import com.example.calculate.databinding.MasterFragmentBinding;

public class FailedFragment extends Fragment {
    MyViewModel myViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myViewModel = new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        FragmentFailedBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_failed,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        myViewModel.winFlag = false;
        binding.btnBack.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_failedFragment_to_masterFragment));
        return binding.getRoot();
    }
}