package com.example.displaytestgertec.ui.touchtest

import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.lifecycle.ViewModelProvider
import com.example.displaytestgertec.R
import com.example.displaytestgertec.databinding.ActivityTouchTestBinding
import com.example.displaytestgertec.ui.touchtest.viewmodel.TouchTestViewModel

class TouchTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTouchTestBinding
    private lateinit var viewModel: TouchTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTouchTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TouchTestViewModel::class.java]

        setupButtons()
        setupObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startCountDown()
    }

    private fun setupObservers() {
        viewModel.timeout.observe(this){ timeout ->
            if(timeout){
                Toast.makeText(
                    this,
                    resources.getString(R.string.timeout),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }

        viewModel.touchedSquaresIndexes.observe(this){ touchedSquaresIndexes ->
            if(touchedSquaresIndexes.isNullOrEmpty()){
                finishTest()
            }
        }
    }

    private fun setupButtons() {
        binding.rootLayout.setOnTouchListener{ _, event ->
            setupSquareCoordinate(event.x,event.y)
            true
        }
    }

    private fun setupSquareCoordinate(x: Float, y: Float) {
        val gridRect = Rect()
        binding.gridLayout.getGlobalVisibleRect(gridRect)

        binding.gridLayout.forEachIndexed { index, square ->
            val squareRect = Rect()
            square.getGlobalVisibleRect(squareRect)

            viewModel.verifySquareCoordinate(gridRect, squareRect, x, y, index).let {
                if(it) square.setBackgroundColor(Color.GREEN)
            }
        }
    }

    private fun finishTest() {
        binding.btnSuccess.let{ btnSuccess ->
            btnSuccess.visibility = View.VISIBLE

            btnSuccess.setOnClickListener {
                viewModel.stopCountDown()
                binding.gridLayout.let{ gridLayout ->
                    gridLayout.forEach { square ->
                        square.setBackgroundColor(ContextCompat.getColor(this, R.color.square_off))
                    }
                }

                Toast.makeText(
                    this,
                    resources.getString(R.string.teste_concluido),
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }

}