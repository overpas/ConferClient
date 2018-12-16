package by.overpass.conferclient.ui.register.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.register.fragment.RegisterFragment
import by.overpass.conferclient.util.replaceFragment

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (savedInstanceState == null) {
            replaceFragment(RegisterFragment.newInstance(), R.id.flRegistrationFragmentContainer, false)
        }
    }
}