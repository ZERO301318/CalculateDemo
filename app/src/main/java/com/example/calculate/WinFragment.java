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

import com.example.calculate.databinding.FragmentWinBinding;
import com.example.calculate.databinding.MasterFragmentBinding;

public class WinFragment extends Fragment {
    MyViewModel myViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myViewModel = new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        FragmentWinBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_win,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        myViewModel.winFlag = false;
        binding.button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_winFragment_to_masterFragment));
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        myViewModel.save();
    }
}