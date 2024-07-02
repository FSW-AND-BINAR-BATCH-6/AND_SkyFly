package com.kom.skyfly.presentation.home.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentHomeCalendarBinding
import com.kom.skyfly.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class HomeCalendarFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentHomeCalendarBinding
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    private val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))
    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeCalendarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()

        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setBottomSheetMaxHeight(maxPeekHeight)

        val daysOfWeek = DayOfWeek.entries
        val titlesContainer = view.findViewById<ViewGroup>(R.id.titlesContainer)
        titlesContainer.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                textView.text = title
            }

        binding.calendarView.dayBinder =
            object : MonthDayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)

                @SuppressLint("SetTextI18n")
                override fun bind(
                    container: DayViewContainer,
                    data: CalendarDay,
                ) {
                    container.day = data
                    val textView = container.textView
                    textView.text = data.date.dayOfMonth.toString()

                    when (data.position) {
                        DayPosition.MonthDate -> {
                            textView.visibility = View.VISIBLE
                            if (data.date.isBefore(LocalDate.now())) {
                                textView.setTextColor(Color.GRAY)
                                textView.isClickable = false
                                textView.isEnabled = false
                            } else {
                                when {
                                    startDate == data.date && (mainViewModel.roundTrip.value == false || endDate == data.date) -> {
                                        textView.setTextColor(Color.WHITE)
                                        textView.setBackgroundResource(R.drawable.selection_background)
                                        binding.tvDepartureDate.text = data.date.format(dateFormatter)
                                        mainViewModel.setStartTime(data.date.format(dateFormatter))
//                                        if (mainViewModel.roundTrip.value == true) {
//                                            binding.tvBackDate.text = data.date.format(dateFormatter)
//                                        } else {
//                                            binding.tvBackDate.text = getString(R.string.text_dash)
//                                        }
                                    }
                                    startDate == data.date -> {
                                        textView.setTextColor(Color.WHITE)
                                        textView.setBackgroundResource(R.drawable.selection_background)
                                        binding.tvDepartureDate.text = data.date.format(dateFormatter)
                                        mainViewModel.setStartTime(data.date.format(dateFormatter))
                                    }
                                    endDate == data.date -> {
                                        textView.setTextColor(Color.WHITE)
                                        textView.setBackgroundResource(R.drawable.selection_background)
//                                        binding.tvBackDate.text = data.date.format(dateFormatter)
                                        mainViewModel.setReturnTime(data.date.format(dateFormatter))
                                    }
                                    startDate != null && endDate != null && (data.date > startDate && data.date < endDate) -> {
                                        textView.setTextColor(Color.WHITE)
                                        textView.setBackgroundResource(R.drawable.range_background)
                                    }
                                    else -> {
                                        textView.setTextColor(Color.BLACK)
                                        textView.background = null
                                    }
                                }
                            }
                        }
                        else -> {
                            textView.visibility = View.INVISIBLE
                        }
                    }
                    textView.alpha = if (data.position == DayPosition.MonthDate) 1f else 0.3f
                }
            }
        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                override fun bind(
                    container: MonthViewContainer,
                    data: CalendarMonth,
                ) {
                    if (container.titlesContainer.tag == null) {
                        container.titlesContainer.tag = data.yearMonth
                        container.titlesContainer.children.map { it as TextView }
                            .forEachIndexed { index, textView ->
                                val dayOfWeek = daysOfWeek[index]
                                val title =
                                    dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                                textView.text = title
                            }
                    }
                }
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val firstDayOfWeek = firstDayOfWeekFromLocale()
        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.monthScrollListener = {
            updateMonthYearText(it.yearMonth)
        }

        updateMonthYearText(currentMonth)

        binding.ivPreviousMonth.setOnClickListener {
            binding.calendarView.smoothScrollToMonth(
                binding.calendarView.findFirstVisibleMonth()?.yearMonth?.minusMonths(1) ?: YearMonth.now().minusMonths(1),
            )
        }

        binding.ivNextMonth.setOnClickListener {
            binding.calendarView.smoothScrollToMonth(
                binding.calendarView.findFirstVisibleMonth()?.yearMonth?.plusMonths(1) ?: YearMonth.now().plusMonths(1),
            )
        }
    }

    private fun updateMonthYearText(yearMonth: YearMonth) {
        val monthYearText =
            yearMonth.month.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault(),
            ) + " " + yearMonth.year
        binding.root.findViewById<TextView>(R.id.tv_month_year).text = monthYearText
    }

    private fun setBottomSheetMaxHeight(maxPeekHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxPeekHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivHomeCloseCalendar.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            dismiss()
        }
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.calendarDayText)
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                if (day.position == DayPosition.MonthDate) {
                    if (mainViewModel.roundTrip.value == false) {
                        // Single date selection logic
                        startDate = day.date
                        endDate = day.date
                    } else {
                        // Range selection logic
                        if (startDate == null || endDate != null) {
                            startDate = day.date
                            endDate = null
                        } else if (day.date < startDate) {
                            startDate = day.date
                        } else {
                            endDate = day.date
                        }
                    }
                    binding.calendarView.notifyCalendarChanged()
                }
            }
        }
    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val titlesContainer = view as ViewGroup
    }
}
