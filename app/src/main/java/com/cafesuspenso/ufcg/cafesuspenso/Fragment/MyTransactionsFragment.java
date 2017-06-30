package com.cafesuspenso.ufcg.cafesuspenso.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cafesuspenso.ufcg.cafesuspenso.Adapter.TransactionAdapter;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Transaction;
import com.cafesuspenso.ufcg.cafesuspenso.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MyTransactionsFragment extends Fragment {
    private List<Transaction> transactions;
    private TransactionAdapter transactionAdapter;
    private RecyclerView recyclerView;
    private String title;
    private TextView titleFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_transactions, container, false);
        titleFragment = (TextView) v.findViewById(R.id.title);
        titleFragment.setText(title);

        transactions = fetchTransactions();
        this.transactionAdapter = new TransactionAdapter(getContext(), transactions);

        this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        registerForContextMenu(recyclerView);
        this.recyclerView.setAdapter(transactionAdapter);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        transactionAdapter.update(transactions);

        return v;
    }

    private List<Transaction> fetchTransactions() {
        List<Transaction> result = new ArrayList<>();
        Transaction t;

        Random rand = new Random();
        Date d;
        for(int i =0;i < 10;i++){
            d = new Date();
            d.setDate(rand.nextInt(28) + 1);
            if(i % 2 == 0)
                t = new Transaction("Café do João", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_kI9_JqAXLig08HI_eS69-2vJ09gd9bcXhArdhQXL4m2yA0c8",d);
            else if (i % 3 == 0)
                t = new Transaction("Café da Maria", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVsVLM1WZCgWPzW5j3feNsTPBSLDDxozCCNmEwJjAylHpgv3onfA",d);
            else
                t = new Transaction("Café do Bairro", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAwI9Db5c-fxdBiWn-ErVO2m3zzp96Cdgtv8f2iznymYhrK8CZcQ",d);
            result.add(t);
        }

        Collections.sort(result, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction, Transaction t1) {
                if(transaction.getDate().after(t1.getDate()))
                    return -1;
                else
                    return 1;
            }
        });
        return  result;
    }

    public void changeTitle(String title){
        this.title = title;
    }
}
