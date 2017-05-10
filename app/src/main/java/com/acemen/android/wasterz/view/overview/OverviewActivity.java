package com.acemen.android.wasterz.view.overview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acemen.android.wasterz.R;
import com.acemen.android.wasterz.domain.AbstractUsecase;
import com.acemen.android.wasterz.domain.overview.usecase.LoadWastes;
import com.acemen.android.wasterz.executor.UsecaseHandler;
import com.acemen.android.wasterz.repository.remote.RemoteWastesRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.txtListWastes1)
    TextView txtListWastes;

    @Nullable
    @BindView(R.id.btLoadWastes1)
    Button btLoadWastes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overview);


        ButterKnife.bind(this);

        btLoadWastes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsecaseHandler.getInstance().executeUsecase(
                        new LoadWastes(RemoteWastesRepository.getInstance()),
                        new LoadWastes.Request(),
                        new AbstractUsecase.UsecaseCallback<LoadWastes.Response>() {
                            @Override
                            public void onSuccess(LoadWastes.Response response) {
                                txtListWastes.setText(response.getWastes().get(0).getAddress());
                            }

                            @Override
                            public void onError() {
                                txtListWastes.setText("Erreur lors du chargement des données");
                            }
                        }
                );
            }
        });
    }
}
