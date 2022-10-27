package com.godminq.noting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.godminq.noting.databinding.FragmentWritingMemoBinding

class WritingMemoFragment : Fragment() {

    lateinit var binding: FragmentWritingMemoBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritingMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            mainActivity.hideKeyboard()
        }

        binding.btnBack.setOnClickListener {
            mainActivity.goBack()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.binding.btnAddMemo.visibility =View.VISIBLE
    }


}