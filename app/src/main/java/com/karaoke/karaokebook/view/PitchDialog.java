package com.karaoke.karaokebook.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.DialogPitchBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;

public class PitchDialog extends Dialog {
    SongCellData data;
    DatabaseViewModel databaseViewModel;
    MutableLiveData<Integer> pitch;
    LifecycleOwner lifecycleOwner;
    public PitchDialog(@NonNull Context context, SongCellData data, DatabaseViewModel databaseViewModel, LifecycleOwner lifecycleOwner) {
        super(context);
        this.data = data;
        this.databaseViewModel = databaseViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogPitchBinding binding = DialogPitchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pitch = new MutableLiveData<>();
        pitch.setValue(data.getPitch());
        binding.setPitch(pitch);

        binding.setLifecycleOwner(lifecycleOwner);
        binding.increasePitchButton.setOnClickListener(view -> pitch.setValue(pitch.getValue() + 1));

        binding.decreasePitchButton.setOnClickListener(view -> pitch.setValue(pitch.getValue() - 1));

        binding.cancelChangePitchButton.setOnClickListener(view -> dismiss());

        binding.savePitchButton.setOnClickListener(view -> {
            databaseViewModel.setPitch(data.getNumber(), pitch.getValue());

            dismiss();
        });

        pitch.observe(lifecycleOwner, value -> Log.e("TEST", String.valueOf(value)));
    }
}
