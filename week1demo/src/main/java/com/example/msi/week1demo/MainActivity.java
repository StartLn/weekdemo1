package com.example.msi.week1demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.msi.week1demo.fragment.CircleFragment;
import com.example.msi.week1demo.fragment.FirstFragment;
import com.example.msi.week1demo.fragment.OrderFormFragment;
import com.example.msi.week1demo.fragment.PersonateFragment;
import com.example.msi.week1demo.fragment.ShoppingCartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ragement)
    FrameLayout ragement;
    @BindView(R.id.page_first)
    RadioButton pageFirst;
    @BindView(R.id.page_circle)
    RadioButton pageCircle;
    @BindView(R.id.page_shopping_cart)
    RadioButton pageShoppingCart;
    @BindView(R.id.page_order_form)
    RadioButton pageOrderForm;
    @BindView(R.id.page_personage)
    RadioButton pagePersonage;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private Unbinder unbinder;
    private FragmentManager manager;
    private PersonateFragment personateFragment;
    private OrderFormFragment orderFormFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private CircleFragment circleFragment;
    private FirstFragment firstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        initData();
        manager.beginTransaction().replace(R.id.ragement,firstFragment).commit();
        setOnclickListener();
    }

    private void setOnclickListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.page_first:
                        manager.beginTransaction().replace(R.id.ragement,firstFragment).commit();
                        break;
                    case R.id.page_circle:
                        manager.beginTransaction().replace(R.id.ragement,circleFragment).commit();
                        break;
                    case R.id.page_shopping_cart:
                        manager.beginTransaction().replace(R.id.ragement,shoppingCartFragment).commit();
                        break;
                    case R.id.page_order_form:
                        manager.beginTransaction().replace(R.id.ragement,orderFormFragment).commit();
                        break;
                    case R.id.page_personage:
                        manager.beginTransaction().replace(R.id.ragement,personateFragment).commit();
                        break;
                    default :break;
                }
            }
        });
    }

    private void initData() {
        firstFragment = new FirstFragment();
        circleFragment = new CircleFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        orderFormFragment = new OrderFormFragment();
        personateFragment = new PersonateFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
