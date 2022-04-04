package com.example.redsocialapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class DialogManager {

    companion object{
        fun progressBarFragment(context: Fragment?): ProgressBar {
            val fm: FragmentManager? = context?.childFragmentManager
            fm?.let { eliminaDialogFragment(it, ProgressBar.TAG) }
            val dialogFragment: ProgressBar =
                ProgressBar.newInstance()
            dialogFragment.isCancelable = false
            try {
                fm?.let { dialogFragment.show(it, ProgressBar.TAG) }
            } catch (ignored: Exception) {
            }
            return dialogFragment
        }

        private fun eliminaDialogFragment(fm: FragmentManager, tag: String) {
            val fragment: Fragment? = fm.findFragmentByTag(tag)
            if (fragment != null) {
                try {
                    fm.beginTransaction().remove(fragment).commit()
                } catch (ignored: java.lang.Exception) {
                }
            }
        }
    }
}