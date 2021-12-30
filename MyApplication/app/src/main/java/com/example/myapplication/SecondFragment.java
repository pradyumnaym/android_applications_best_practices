package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentSecondBinding;

import java.util.Random;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int count = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
        String substituted_text = getString(R.string.second_fragment_heading, count);
        binding.textviewSecond.setText(substituted_text);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragmentDirections.ActionSecondFragmentToFirstFragment action =
                        SecondFragmentDirections.actionSecondFragmentToFirstFragment();
                action.setCount(count);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(action);
            }
        });

        Random  random = new java.util.Random();
        int randomNumber = 0;
        if (count > 0) {
            randomNumber = random.nextInt(count + 1);
        }
        binding.countTextview.setText(Integer.toString(randomNumber));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}