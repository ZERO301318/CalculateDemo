package com.example.calculate;

import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculate.databinding.MasterFragmentBinding;

public class MasterFragment extends Fragment {
    MyViewModel myViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //尽量不要使用getActivity(),因为可能为null
        myViewModel = new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        MasterFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.master_fragment,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        myViewModel.getCurrentScore().setValue(0);
        binding.button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_masterFragment_to_questionFragment));
        return binding.getRoot();
    }
}