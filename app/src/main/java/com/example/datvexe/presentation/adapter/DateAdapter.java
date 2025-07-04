package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private List<Date> dates = new ArrayList<>();
    private Date selectedDate;
    private OnDateSelectedListener listener;
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    private final SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEE", new Locale("vi"));

    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
        if (!dates.isEmpty() && selectedDate == null) {
            selectedDate = dates.get(0);
        }
        notifyDataSetChanged();
    }

    public void selectDate(Date date) {
        if (date != null) {
            int oldPosition = getPositionForDate(selectedDate);
            selectedDate = date;
            int newPosition = getPositionForDate(date);
            if (oldPosition != -1) notifyItemChanged(oldPosition);
            if (newPosition != -1) notifyItemChanged(newPosition);
        }
    }

    private int getPositionForDate(Date date) {
        if (date == null) return -1;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        for (int i = 0; i < dates.size(); i++) {
            cal1.setTime(date);
            cal2.setTime(dates.get(i));
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = dates.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Format date components
        String dayOfWeek = dayOfWeekFormat.format(date);
        String day = dayFormat.format(date);
        String month = "Th" + monthFormat.format(date);

        // Set text
        holder.tvDayOfWeek.setText(dayOfWeek);
        holder.tvDate.setText(day);
        holder.tvMonth.setText(month);

        // Handle selection state
        boolean isSelected = selectedDate != null && isSameDay(date, selectedDate);
        holder.itemView.setSelected(isSelected);
        
        // Update text colors based on selection
        int textColor = isSelected ? 
            holder.itemView.getContext().getColor(R.color.date_selected_text_color) :
            holder.itemView.getContext().getColor(R.color.date_text_color);
            
        holder.tvDayOfWeek.setTextColor(textColor);
        holder.tvDate.setTextColor(textColor);
        holder.tvMonth.setTextColor(textColor);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                selectDate(date);
                listener.onDateSelected(date);
            }
        });
    }

    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayOfWeek;
        TextView tvDate;
        TextView tvMonth;

        DateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMonth = itemView.findViewById(R.id.tvMonth);
        }
    }
} 
