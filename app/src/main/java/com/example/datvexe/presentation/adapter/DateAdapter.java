package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.databinding.ItemDateBinding;

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
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", new Locale("vi"));
    private final SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEE", new Locale("vi"));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", new Locale("vi"));

    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
        if (!dates.isEmpty() && selectedDate == null) {
            selectedDate = dates.get(0);
        }
        notifyDataSetChanged();
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * Select a specific date and update the UI
     * @param date The date to select
     */
    public void selectDate(Date date) {
        if (date == null || dates.isEmpty()) return;
        
        // Find the matching date in our list
        String dateString = dateFormat.format(date);
        int position = -1;
        
        for (int i = 0; i < dates.size(); i++) {
            if (dateFormat.format(dates.get(i)).equals(dateString)) {
                position = i;
                break;
            }
        }
        
        if (position != -1) {
            Date oldSelected = selectedDate;
            selectedDate = dates.get(position);
            
            // Only update if the selection has changed
            if (!selectedDate.equals(oldSelected)) {
                int oldPos = oldSelected != null ? dates.indexOf(oldSelected) : -1;
                if (oldPos != -1) {
                    notifyItemChanged(oldPos);
                }
                notifyItemChanged(position);
            }
            
            // Scroll to the selected position
            if (recyclerView != null) {
                recyclerView.smoothScrollToPosition(position);
            }
        }
    }
    
    private RecyclerView recyclerView;
    
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }
    
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDateBinding binding = ItemDateBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = dates.get(position);
        holder.bind(date);

        holder.itemView.setOnClickListener(v -> {
            if (selectedDate != date) {
                Date oldSelected = selectedDate;
                selectedDate = date;
                notifyItemChanged(dates.indexOf(oldSelected));
                notifyItemChanged(position);
                if (listener != null) {
                    listener.onDateSelected(date);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateBinding binding;

        public DateViewHolder(@NonNull ItemDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Date date) {
            binding.tvDay.setText(dayFormat.format(date));
            binding.tvDayOfWeek.setText(dayOfWeekFormat.format(date));

            boolean isSelected = date.equals(selectedDate);
            int textColor = isSelected ? 
                    ContextCompat.getColor(itemView.getContext(), R.color.white) :
                    ContextCompat.getColor(itemView.getContext(), R.color.black);
            
            binding.tvDay.setTextColor(textColor);
            binding.tvDayOfWeek.setTextColor(textColor);
            
            if (isSelected) {
                binding.tvDay.setBackgroundResource(R.drawable.circle_background_selected);
            } else {
                binding.tvDay.setBackgroundResource(0);
            }
        }
    }
} 
