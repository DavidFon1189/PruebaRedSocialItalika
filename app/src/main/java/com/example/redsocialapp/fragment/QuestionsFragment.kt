package com.example.redsocialapp.fragment
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.redsocialapp.DialogManager
import com.example.redsocialapp.R
import com.example.redsocialapp.api.BaseFragment
import com.example.redsocialapp.api.RetrofitClient
import com.example.redsocialapp.databinding.PerfilFragmentBinding
import com.example.redsocialapp.databinding.QuestionsFragmentBinding
import com.example.redsocialapp.model.ResponseQuestions
import com.example.redsocialapp.presenter.QuestionsContract
import com.example.redsocialapp.presenter.QuestionsPresenter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Response

class QuestionsFragment: BaseFragment<QuestionsPresenter>(), QuestionsContract.View {
    private var _binding: QuestionsFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuestionsFragmentBinding.inflate(inflater, container, false)
        val view = binding?.root
        getGetQuestions()
        return view
    }
    override fun createPresenter(context: BaseFragment<QuestionsPresenter>): QuestionsPresenter {
        return QuestionsPresenter(this, RetrofitClient)
    }

    override fun getGetQuestions() {
        presenter.getGetQuestions()
    }

    override fun succesGetQuestions(response: Response<ResponseQuestions>) {
        val tiendas: MutableList<PieEntry> = ArrayList()
        var percentaje = 0F
        var text = ""
        response.body()?.questions?.forEach { element ->
            element.chartData.forEach{ data ->
                percentaje = data.percetnage.toFloat()
            }
            element.chartData.forEach{ data ->
                text = data.text
            }
            tiendas.add(PieEntry(percentaje,text))
        }
        val pieDataSet = PieDataSet(tiendas,"Tiendas")
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 20f
        pieDataSet.colors = ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS)

        val pieData = PieData(pieDataSet)
        binding?.pieChart?.data = pieData
        binding?.pieChart?.description?.isEnabled = false
        binding?.pieChart?.centerText = "Tiendas"
        binding?.pieChart?.animate()

    }

    override fun failGetQuestions(message: String?) {
        val buildDialog = AlertDialog.Builder(activity?.applicationContext!!)
        buildDialog.setTitle("ERROR")
        buildDialog.setMessage("Error de Datos")
        buildDialog.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = buildDialog.create()
        dialog.show()
    }

    override fun failGetQuestionsServer(message: String?) {
        val buildDialog = AlertDialog.Builder(activity?.applicationContext!!)
        buildDialog.setTitle("ERROR")
        buildDialog.setMessage("Error de Servicio")
        buildDialog.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = buildDialog.create()
        dialog.show()
    }

    override fun loadingData() {
        DialogManager.progressBarFragment(this)
    }

    override fun hideDataLoad() {
        DialogManager.progressBarFragment(this).dismiss()
    }
}