package com.example.calculate;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculate.databinding.FragmentQuestionBinding;

public class QuestionFragment extends Fragment implements View.OnClickListener {
    MyViewModel myViewModel;
    FragmentQuestionBinding binding;
    StringBuilder stringBuilder;
    boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myViewModel = new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        //第一次进入页面，生成题目
        if(myViewModel.getCurrentScore().getValue() == 0)
            myViewModel.generator();
        stringBuilder = new StringBuilder();
        setLinstener();
        return binding.getRoot();
    }

    void setLinstener(){
        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn6.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);
        binding.btn9.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);
        binding.btnEqual.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if(flag){
            stringBuilder.setLength(0);
            flag = false;
        }
        switch (v.getId()){
            case R.id.btn0:
                stringBuilder.append("0");
                break;
            case R.id.btn1:
                stringBuilder.append("1");
                break;
            case R.id.btn2:
                stringBuilder.append("2");
                break;
            case R.id.btn3:
                stringBuilder.append("3");
                break;
            case R.id.btn4:
                stringBuilder.append("4");
                break;
            case R.id.btn5:
                stringBuilder.append("5");
                break;
            case R.id.btn6:
                stringBuilder.append("6");
                break;
            case R.id.btn7:
                stringBuilder.append("7");
                break;
            case R.id.btn8:
                stringBuilder.append("8");
                break;
            case R.id.btn9:
                stringBuilder.append("9");
                break;
            case R.id.btn_clear:
                stringBuilder.setLength(0);
                break;
            case R.id.btn_equal:
                //回答正确
                if(stringBuilder.toString().equals(myViewModel.getAnswer().getValue().toString())){
                    myViewModel.answerCorrect();
                    stringBuilder.setLength(0);
                    stringBuilder.append(getString(R.string.right_answer));
                    flag = true;
                }else{
                    if(myViewModel.winFlag){
                        NavController controller = Navigation.findNavController(v);
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                    }else{
                        Navigation.findNavController(v).navigate(R.id.action_questionFragment_to_failedFragment);
                    }
                }
                break;
        }
        if(stringBuilder.length() == 0){
            binding.myAnswer.setText(getString(R.string.your_answer));
        }else {
            binding.myAnswer.setText(stringBuilder.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        myViewModel.save();
    }
}