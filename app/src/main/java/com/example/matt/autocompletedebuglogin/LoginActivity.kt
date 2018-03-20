package com.example.matt.autocompletedebuglogin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.auto_complete_layout.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (BuildConfig.DEBUG) {
            prepareAutocomplete()
        }
    }

    private fun prepareAutocomplete() {
        val pairs = resources.getStringArray(R.array.accounts).map {
            val loginAndPassword = it.split(":")
            Pair(loginAndPassword.first(), loginAndPassword.last())
        }
        val map = pairs.toMap()
        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.email)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, pairs.map { it.first })
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            password.setText(map[adapter.getItem(position)])
        }
        autoCompleteTextView.setAdapter(adapter)
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }
}
