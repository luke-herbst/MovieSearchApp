package iu.c323.fall2024.project9


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import iu.c323.fall2024.project9.databinding.FragmentLoginBinding

private const val TAG = "LoginFragment"
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Success!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigate_to_search)
                } else {
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnToSignUp.setOnClickListener{
            findNavController().navigate(R.id.navigate_to_signUp)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}