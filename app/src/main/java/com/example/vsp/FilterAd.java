package com.example.vsp;

import android.widget.Filter;

import com.example.vsp.adapters.AdapterAd;
import com.example.vsp.models.ModelAd;

import java.util.ArrayList;

public class FilterAd extends Filter {
    private AdapterAd adapter;
    private ArrayList<ModelAd> filterList;

    public FilterAd(AdapterAd adapter, ArrayList<ModelAd> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();

            ArrayList<ModelAd> filterModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {

                if (filterList.get(i).getBrand().toUpperCase().contains(constraint) || filterList.get(i).getCategory().toUpperCase().contains(constraint)
                        || filterList.get(i).getCondition().toUpperCase().contains(constraint) || filterList.get(i).getTitle().toUpperCase().contains(constraint)

                ) {
                    filterModels.add(filterList.get(i));
                }

            }
            results.count = filterModels.size();
            results.values = filterModels;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.adArrayList = (ArrayList<ModelAd>) results.values;
        adapter.notifyDataSetChanged();

    }
}





//
//import android.widget.Filter;
//
//import com.example.vsp.adapters.AdapterAd;
//import com.example.vsp.models.ModelAd;
//
//import java.util.ArrayList;
//
//public class FilterAd extends Filter {
//    private AdapterAd adapter;
//    private ArrayList<ModelAd> filterList;
//
//    public FilterAd(AdapterAd adapter, ArrayList<ModelAd> filterList) {
//        this.adapter = adapter;
//        this.filterList = filterList;
//    }
//
//    @Override
//    protected FilterResults performFiltering(CharSequence constraint) {
//        FilterResults results = new FilterResults();
//
//        if(constraint != null && constraint.length() > 0) {
//            String constraintStr = constraint.toString().toUpperCase();
//
//            ArrayList<ModelAd> filterModels = new ArrayList<>();
//            for (ModelAd model : filterList) {
//                if (model.getBrand().toUpperCase().contains(constraintStr) ||
//                        model.getCategory().toUpperCase().contains(constraintStr) ||
//                        model.getCondition().toUpperCase().contains(constraintStr) ||
//                        model.getTitle().toUpperCase().contains(constraintStr)) {
//                    filterModels.add(model);
//                }
//            }
//            results.count = filterModels.size();
//            results.values = filterModels;
//        } else {
//            results.count = filterList.size();
//            results.values = filterList;
//        }
//        return results;
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")  // Suppress unchecked cast warning
//    protected void publishResults(CharSequence constraint, FilterResults results) {
//        adapter.adArrayList = (ArrayList<ModelAd>) results.values;
//        adapter.notifyDataSetChanged();
//    }
//}
