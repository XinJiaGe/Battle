package com.jock.pickerview.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.View;

import com.jock.pickerview.R;
import com.jock.pickerview.adapter.NumericWheelAdapter;
import com.jock.pickerview.lib.WheelView;
import com.jock.pickerview.listener.OnItemSelectedListener;
import com.jock.pickerview.view.TimePickerView.Type;

public class WheelTime
{
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;

	private Type type;
	public static final int DEFULT_START_YEAR = 1990;
	public static final int DEFULT_END_YEAR = 2100;
	private int startYear = DEFULT_START_YEAR;
	private int endYear = DEFULT_END_YEAR;
	private int oneYueLimit = 1;
	private int oneRiLimit = 1;

	public WheelTime(View view)
	{
		super();
		this.view = view;
		type = Type.ALL;
		setView(view);
	}

	public WheelTime(View view, Type type)
	{
		super();
		this.view = view;
		this.type = type;
		setView(view);
	}

	public void setPicker(int year, int month, int day)
	{
		this.setPicker(year, month, day, 0, 0);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void setPicker(int year, int month, int day, int h, int m)
	{
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big =
		{ "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little =
		{ "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		Context context = view.getContext();
		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
		wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
		wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(oneYueLimit, 12, "%02d"));
		wv_month.setLabel(context.getString(R.string.pickerview_month));
		if(oneYueLimit!=1){
			wv_month.setCurrentItem(0);
		}else{
			wv_month.setCurrentItem(month);
		}


		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		// 判断大小月及是否闰年,用来确定"日"的数据
		System.out.println("month->" + month);
		if (list_big.contains(String.valueOf(month + 1)))
		{
			wv_day.setAdapter(new NumericWheelAdapter(oneRiLimit, 31, "%02d"));
		} else if (list_little.contains(String.valueOf(month + 1)))
		{
			wv_day.setAdapter(new NumericWheelAdapter(oneRiLimit, 30, "%02d"));
		} else
		{
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(oneRiLimit, 29, "%02d"));
			else
				wv_day.setAdapter(new NumericWheelAdapter(oneRiLimit, 28, "%02d"));
		}
		wv_day.setLabel(context.getString(R.string.pickerview_day));
		if(oneRiLimit!=1){
			wv_day.setCurrentItem(0);
		}else{
			wv_day.setCurrentItem(day - 1);
		}

		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23, "%02d"));
		wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
		wv_hours.setCurrentItem(h);

		wv_mins = (WheelView) view.findViewById(R.id.min);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
		wv_mins.setCurrentItem(m);

		// 添加"年"监听
		OnItemSelectedListener wheelListener_year = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(int index)
			{
				int yearCurrentItem = 1;
				int resetRi = 1;
				if(index == startYear){
					wv_month.setAdapter(new NumericWheelAdapter(oneYueLimit, 12, "%02d"));
					yearCurrentItem = wv_month.getCurrentItem();
					if(yearCurrentItem<oneYueLimit){
						wv_month.setCurrentItem(0);
					}else{
						wv_month.setCurrentItem(yearCurrentItem-oneYueLimit);
					}
					resetRi = oneRiLimit;
//					wv_day.setCurrentItem(0);
				}else{
					yearCurrentItem = wv_month.getCurrentItem();
					wv_month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
					resetRi = 1;
					wv_month.setCurrentItem(yearCurrentItem-1);
				}

				int dayCurrentItem = wv_day.getCurrentItem();

				int year_num = index ;
				System.out.println("year_num--->"+year_num);
				// 判断大小月及是否闰年,用来确定"日"的数据
				int maxItem = 30;
				if (list_big.contains(String.valueOf(wv_month.getCurrentItem())))
				{
					wv_day.setAdapter(new NumericWheelAdapter(resetRi, 31, "%02d"));
					maxItem = 31;
				} else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() )))
				{
					wv_day.setAdapter(new NumericWheelAdapter(resetRi, 30, "%02d"));
					maxItem = 30;
				} else
				{
					if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
					{
						wv_day.setAdapter(new NumericWheelAdapter(resetRi, 29, "%02d"));
						maxItem = 29;
					} else
					{
						wv_day.setAdapter(new NumericWheelAdapter(resetRi, 28, "%02d"));
						maxItem = 28;
					}
				}
				if(index == startYear){
					if (dayCurrentItem < oneRiLimit){
						wv_day.setCurrentItem(0);
					}else{
						if (wv_day.getCurrentItem() > maxItem - 1){
							wv_day.setCurrentItem(maxItem - 1);
						}else{
							wv_day.setCurrentItem(dayCurrentItem-oneRiLimit);
						}
					}
				}else{
					if (wv_day.getCurrentItem() > maxItem - 1){
						wv_day.setCurrentItem(maxItem - 1);
					}else{
						wv_day.setCurrentItem(dayCurrentItem -1);
					}
				}
			}
		};
		// 添加"月"监听
		OnItemSelectedListener wheelListener_month = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(int index)
			{
				int resetRi = 1;
				if(index == oneYueLimit){
					resetRi = oneRiLimit;
				}else{
					resetRi = 1;
				}

				int dayCurrentItem = wv_day.getCurrentItem();
				int month_num = index ;
				System.out.println("month_num--->"+month_num);
				int maxItem = 30;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(resetRi, 31, "%02d"));
					maxItem = 31;
				} else if (list_little.contains(String.valueOf(month_num)))
				{
					wv_day.setAdapter(new NumericWheelAdapter(resetRi, 30, "%02d"));
					maxItem = 30;
				} else
				{
					if (((wv_year.getCurrentItem() ) % 4 == 0 && (wv_year.getCurrentItem() ) % 100 != 0) || (wv_year.getCurrentItem() ) % 400 == 0)
					{
						wv_day.setAdapter(new NumericWheelAdapter(resetRi, 29, "%02d"));
						maxItem = 29;
					} else
					{
						wv_day.setAdapter(new NumericWheelAdapter(resetRi, 28, "%02d"));
						maxItem = 28;
					}
				}
				if(index == oneYueLimit){
					if (dayCurrentItem < oneRiLimit){
						wv_day.setCurrentItem(0);
					}else{
						if (wv_day.getCurrentItem() > maxItem - 1){
							wv_day.setCurrentItem(maxItem - 1);
						}else{
							wv_day.setCurrentItem(dayCurrentItem-oneRiLimit);
						}
					}
				}else{
					if (wv_day.getCurrentItem() > maxItem - 1){
						wv_day.setCurrentItem(maxItem - 1);
					}else{
						wv_day.setCurrentItem(dayCurrentItem -1);
					}
				}
			}
		};
		wv_year.setOnItemSelectedListener(wheelListener_year);
		wv_month.setOnItemSelectedListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 6;
		switch (type)
		{
		case ALL:
			textSize = 40;
			break;
		case YEAR_MONTH_DAY:
			textSize = 50;
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
			break;
		case HOURS_MINS:
			textSize = 50;
			wv_year.setVisibility(View.GONE);
			wv_month.setVisibility(View.GONE);
			wv_day.setVisibility(View.GONE);
			break;
		case MONTH_DAY_HOUR_MIN:
			textSize = 40;
			wv_year.setVisibility(View.GONE);
			break;
		case YEAR_MONTH:
			textSize = 50;
			wv_day.setVisibility(View.GONE);
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
		}
		wv_day.setTextSize(textSize);
		wv_month.setTextSize(textSize);
		wv_year.setTextSize(textSize);
		wv_hours.setTextSize(textSize);
		wv_mins.setTextSize(textSize);

	}

	/**
	 * 设置是否循环滚动
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic)
	{
		wv_year.setCyclic(cyclic);
		wv_month.setCyclic(cyclic);
		wv_day.setCyclic(cyclic);
		wv_hours.setCyclic(cyclic);
		wv_mins.setCyclic(cyclic);
	}

	public String getTime()
	{
		StringBuffer sb = new StringBuffer();
		// sb.append((wv_year.getCurrentItem() +
		// startYear)).append("-").append((wv_month.getCurrentItem() +
		// 1)).append("-").append((wv_day.getCurrentItem() +
		// 1)).append(" ").append(wv_hours.getCurrentItem()).append(":").append(wv_mins.getCurrentItem());
		sb.append((wv_year.getCurrentItem())).append("-").append((wv_month.getCurrentItem())).append("-").append((wv_day.getCurrentItem())).append(" ").append(wv_hours.getCurrentItem()).append(":").append(wv_mins.getCurrentItem());
		return sb.toString();
	}

	public View getView()
	{
		return view;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	public int getStartYear()
	{
		return startYear;
	}

	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	public int getEndYear()
	{
		return endYear;
	}

	public void setEndYear(int endYear)
	{
		this.endYear = endYear;
	}

	public int getOneYueLimit() {
		return oneYueLimit;
	}

	public void setOneYueLimit(int oneYueLimit) {
		this.oneYueLimit = oneYueLimit;
	}

	public int getOneRiLimit() {
		return oneRiLimit;
	}

	public void setOneRiLimit(int oneRiLimit) {
		this.oneRiLimit = oneRiLimit;
	}
}
