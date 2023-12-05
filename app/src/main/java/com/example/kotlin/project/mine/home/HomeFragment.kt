package com.example.kotlin.project.mine.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin.project.mine.R
import com.example.kotlin.project.mine.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("HomeFragment onCreateView")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}