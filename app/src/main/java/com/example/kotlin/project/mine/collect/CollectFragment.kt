package com.example.kotlin.project.mine.collect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin.project.mine.R

class CollectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("CollectFragment onCreateView")
        return inflater.inflate(R.layout.fragment_collect, container, false)
    }
}