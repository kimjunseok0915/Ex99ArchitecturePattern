package com.mrhi2022.mvp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mrhi2022.mvp.databinding.ActivityMainBinding
import com.mrhi2022.mvp.model.Item
import com.mrhi2022.mvp.presenter.MainContract
import com.mrhi2022.mvp.presenter.MainPresenter

// MainContract 인터페이스에서 기술한 view 라면 가져야할 기능들이 설계된 View 인터페이스를 구현
class MainActivity : AppCompatActivity(), MainContract.View {

    //2. MVP [ Model - View - Presenter ] - view와 model의 완전분리 특징이 가장 두드러짐
    // 1) Model      - MVC패턴의 model과 같은 역할 : 데이터를 저장하는 클래스, 데이터를 제어하는 코드를 가진 클래스들 [ex. Item 클래스, Person 클래스, Retrofit 작업 클래스, DB작업 클래스...]
    // 2) View       - 사용자 볼 화면을 구현하는 목적의 코드가 작성되는 파일들 [ex. activity_main.xml, MainActivity.kt, Fragment.kt...]
    // 3) Presenter  - 뷰와 모델 사이에서 연결하는 역할, interface 로 역할을 정해놓기에 특정 객체를 만들어 참조하여 결합되는 것을 방지함.

    // ** 주요 제작 특징 : view와 presenter가 해야할 작업들을 미리 interface를 통해 설계해 놓음으로서 작업의 역할 구분이 명확하게 보임.

    // # view 참조변수
    lateinit var binding: ActivityMainBinding

    // # presenter 참조변수
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //# presenter 객체 생성 및 참조
        presenter= MainPresenter()
        presenter.initial(this)

        //# view 로서의 역할
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //# view 로서 사용자 이벤트 처리 - presenter 에게 model의 데이터를 제어해 달라고 요청
        binding.btnSave.setOnClickListener { presenter.clickedSave(binding.etName.text.toString(), binding.etEmail.text.toString()) }
        binding.btnLoad.setOnClickListener { presenter.clickedLoad() }
    }

    override fun showData(item: Item) {
        binding.tv.text= "${item.name} - ${item.email}"
    }

    override fun getContext(): Context {
        return this
    }
}

// MVP의 장점
//1. MVC처럼 데이터를 제어하는 코드가 Activity/Fragment 클래스안에 없어서 간결함

// MVP의 단점