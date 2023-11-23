package com.ssafy.diary.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.LoginActivity.Companion.LOGIN_FRAGMENT
import com.ssafy.diary.R
import com.ssafy.diary.databinding.FragmentFindIdBinding
import com.ssafy.diary.util.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindIdFragment : Fragment() {
    private val binding by lazy { FragmentFindIdBinding.inflate(layoutInflater) }
    private val lActivity by lazy { activity as LoginActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // 좌상단 뒤로 가기 아이콘 이벤트 구현
        binding.btnBack.setOnClickListener {
            lActivity.goBack(this)
        }

        // 로그인 프래그먼트로 이동
        binding.btnLogin.setOnClickListener {
            lActivity.moveFragment(LOGIN_FRAGMENT)
        }

        // 아이디 찾기
        binding.btnFindId.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val inflater = layoutInflater.inflate(R.layout.dialog_find_id, null)

            if(email.isNotEmpty()){
                CoroutineScope(Dispatchers.Main).launch {
                    val result = RetrofitUtil.userService.findId(email).body()
                    if(result != null){
                        showDialog(" ${result} ", "1")
                    } else{
                        showDialog("해당 이메일로 등록된 아이디가 없습니다.", "0")
                    }
                }
            } else{
                Toast.makeText(requireContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun showDialog(result: String, result2: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater.inflate(R.layout.dialog_find_id, null)
        val textView = inflater.findViewById<TextView>(R.id.text_id_dialog)
        val ok = inflater.findViewById<Button>(R.id.btn_ok)
        val x = inflater.findViewById<ImageView>(R.id.img_x)

        val txt1 = inflater.findViewById<TextView>(R.id.text_id_dialog_01)
        val txt2 = inflater.findViewById<TextView>(R.id.text_id_dialog_02)

        builder.apply {
            setView(inflater)
        }
        val ad = builder.create()
        ad.show()
        ok.setOnClickListener {
            ad.cancel()
        }

        x.setOnClickListener {
            ad.cancel()
        }

        if(result2 == "0"){
            txt1.visibility = View.GONE
            txt2.visibility = View.GONE
        }
        else{
            txt1.visibility = View.VISIBLE
            txt2.visibility = View.VISIBLE
        }

        textView.text = result

    }

}