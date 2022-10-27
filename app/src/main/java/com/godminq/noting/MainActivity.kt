package com.godminq.noting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.godminq.noting.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //requestPermission()

        // 바깥화면 터치시 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard()
        }
        // 뷰페이저 스와이프 기능 제거
        binding.viewPager.run {
            isUserInputEnabled= false
        }

        val fragmentList = listOf(MemoFragment(), CategoryFragment(), CalendarFragment(), MyInfoFragment())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList // 프래그먼트어댑터에 각 프래그먼트 연결
        binding.viewPager.adapter = adapter

        val tabTitles = listOf( getString(R.string.tab_layout_memo), getString(R.string.tab_layout_category), getString(R.string.tab_layout_calendar), getString(R.string.tab_layout_my_info) )
        // 텝레이아웃과 뷰페이저 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // addMemo 버튼 클릭시
        binding.btnAddMemo.setOnClickListener {
            goWritingMemoFragment()
            binding.btnAddMemo.visibility =View.GONE
        }
    }

    private fun goWritingMemoFragment() {
        val writingMemoFragment = WritingMemoFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.addMemoConsLayout, writingMemoFragment)
        transaction.addToBackStack("writingMemo")
        transaction.commit()
    }

    fun goBack() {
        onBackPressed()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

}
class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragmentList = listOf<Fragment>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}