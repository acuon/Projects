package dev.acuon.gitcommansexplorer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val PRIMARY_OPTIONS = "primary_options"
        const val SECONDARY_OPTIONS = "secondary_options"
        const val LABEL = "label"
        const val VALUE = "value"
        const val USAGE = "usage"
        const val NB = "nb"
        const val FILENAME = "git_command_explorer.json"
    }

    private var primaryOptions = ArrayList<PrimaryOptions>()
    private var primaryOptionsValue = ""
    private var secondaryOptions = ArrayList<SecondaryOptions>()
    private lateinit var jsonFileObject: JSONObject
    private var usage = ""
    private var note = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_git_command.text =
            Html.fromHtml(resources.getString(R.string.git_command_explorer))
        jsonFileObject = JSONObject(loadJSONFromAsset())
        getPrimaryOptions()
        input_first_field.setOnTouchListener { p0, p1 ->
            input_first_field.showDropDown()
            false
        }
        input_second_field.setOnTouchListener { p0, p1 ->
            input_second_field.showDropDown()
            false
        }
        input_first_field.setOnItemClickListener { adapterView, view, pos, l ->
            primaryOptionsValue = primaryOptions.find {
                it.label == input_first_field.text.toString()
            }?.value!!
            dismissKeyboard(input_first_field)
            card_view_second_field.visibility = View.VISIBLE
            text_note.visibility = View.GONE
            card_view_note.visibility = View.GONE
            input_second_field.text.clear()
            text_display_git_command.text = ""
            text_display_note.text = ""
            getSecondaryOptions()
        }
        input_second_field.setOnItemClickListener { adapterView, view, i, l ->
            dismissKeyboard(input_first_field)
            usage = secondaryOptions.find {
                it.label == input_second_field.text.toString()
            }?.usage!!
            note = secondaryOptions.find {
                it.label == input_second_field.text.toString()
            }?.nb!!
            text_note.visibility = if (note == "") View.GONE else View.VISIBLE
            card_view_note.visibility = if (note == "") View.GONE else View.VISIBLE
            text_display_git_command.text = usage
            text_display_note.text = note

        }
    }


    private fun getPrimaryOptions() {
        val jsonPrimaryOptionsArray = jsonFileObject.getJSONArray(PRIMARY_OPTIONS)
        for (i in 0 until jsonPrimaryOptionsArray.length()) {
            val jsonObject = jsonPrimaryOptionsArray.getJSONObject(i)
            val primary = PrimaryOptions()
            primary.value = jsonObject.getString(VALUE)
            primary.label = jsonObject.getString(LABEL)
            primaryOptions.add(primary)
        }
//        primaryOptions.sortWith(Comparator { p1: PrimaryOptions, p2: PrimaryOptions -> p1.label - p2.label })
        primaryOptions.sortBy { primaryOptions -> primaryOptions.label }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, primaryOptions.map {
                it.label
            }
        )
        input_first_field.setAdapter(adapter)
    }

    private fun getSecondaryOptions() {
        secondaryOptions.clear()
        val jsonSecondaryOptionsObject = jsonFileObject.getJSONObject(SECONDARY_OPTIONS)
        val jsonArray = jsonSecondaryOptionsObject.getJSONArray(primaryOptionsValue)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val secondary = SecondaryOptions()
            secondary.value = jsonObject.getString(VALUE)
            secondary.label = jsonObject.getString(LABEL)
            if (jsonObject.has("usage"))
                secondary.usage = jsonObject.getString(USAGE)
            if (jsonObject.has("nb"))
                secondary.nb = jsonObject.getString(NB)
            secondaryOptions.add(secondary)
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, secondaryOptions.map {
                it.label
            }
        )
        input_second_field.setAdapter(adapter)
    }

    private fun Context.dismissKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun loadJSONFromAsset(): String {
        return assets.open(FILENAME).bufferedReader().use {
            it.readText()
        }
    }
}
