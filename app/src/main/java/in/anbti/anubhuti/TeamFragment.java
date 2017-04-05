package in.anbti.anubhuti;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {

    private ArrayAdapter<String> mTeamAdapter;
    GridView gridview;
    String contactNo[] = null;

    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);

        String[] names = {"Ayush Arela",
                "Adarsh Anand",
                "Harsh Shukla",
                "Soumya Dubey",
                "Vishal Kr. Rana",
                "Shubham Srivastava",
                "Anurag Srivastava",
                "Pranesh Dwivedi",
                "Shubham Pratap",
                "Devesh Pandey",
                "Atul Gupta",
                "Adarsh Pandey",
                "Ayush Aman"};

        String[] designation = {"Fest Coordinator",
                "Fest Coordinator",
                "Fest Coordinator",
                "Hospitality",
                "Hospitality",
                "Hospitality",
                "Hospitality",
                "Hospitality",
                "Publicity & Marketing",
                "Publicity & Marketing",
                "Web Developer",
                "Web Developer",
                "App Developer"};

        int[] images = {R.drawable.contact_ayush_arela,
                R.drawable.contact_adarsh_anand,
                R.drawable.contact_harsh_shukla,
                R.drawable.contact_soumya_dubey,
                R.drawable.contact_vishal_rana,
                R.drawable.contact_shubham_srivastava,
                R.drawable.contact_anurag_srivastava,
                R.drawable.contact_pranesh_dwivedi,
                R.drawable.contact_shubham_pratap,
                R.drawable.contact_devesh_pandey,
                R.drawable.contact_atul_gupta,
                R.drawable.contact_adarsh_pandey,
                R.drawable.contact_ayush_aman};

        contactNo = new String[]{"0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789"};

        final String[] emailID = {"abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com",
                "abc@xyz.com"};

        List<HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("cimage", Integer.toString(images[i]));
            hm.put("cname", names[i]);
            hm.put("cdesignation", designation[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"cimage", "cname", "cdesignation"};

        // Ids of views in listview_layout
        int[] to = {R.id.team_image_id, R.id.team_name_id, R.id.team_designation_id};

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.grid_item_team, from, to);

//        mTeamAdapter = new ArrayAdapter<>(getActivity(), // The current context (this activity)
//                R.layout.grid_item_team, // The name of the layout ID.
//                R.id.team_name_id, // The ID of the textview to populate.
//                names);

        gridview = (GridView) rootView.findViewById(R.id.team_grid_view);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                dialog(contactNo[position], emailID[position]);
            }
        });

        return rootView;
    }


    public void composeEmail(String address, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + address)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

//    public  void  makeCall(String contactNo) {
//        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
//        phoneIntent.setData(Uri.parse("tel:"+contactNo));
//        startActivity(phoneIntent);
//    }


    public void dialog(final String contactNo, final String emailID) {


        final DialogItem[] items = {
                new DialogItem("Call", R.drawable.icon_call),
                new DialogItem("Message", R.drawable.icon_sms),
                new DialogItem("E-mail", R.drawable.icon_email),//no icon for this one
        };

        ListAdapter dialogAdapter = new ArrayAdapter<DialogItem>(
                getContext(),
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                items) {
            public View getView(int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView) v.findViewById(android.R.id.text1);
//                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(items[position].icon, 0, 0, 0);
                //Put the image on the TextView
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                //Add margin between image and text (support various screen densities)
                int dp5 = (int) (10 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);
                return v;
            }
        };

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Select an option..")
                .setIcon(R.drawable.ic_menu_send)
                .setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String s = "";
                        switch (which) {
                            case 0:
                                s = "Calling this Contact";
                                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                                phoneIntent.setData(Uri.parse("tel:" + contactNo));
                                startActivity(phoneIntent);
                                break;
                            case 1:
                                s = "Messaging this Contact";
                                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contactNo));
                                startActivity(smsIntent);
                                break;
                            case 2:
                                s = "E-mailing this Contact";
                                composeEmail(emailID, "Anubhuti");
                                break;
                        }
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });


        AlertDialog alertDialogB = alertDialog.create();
        alertDialogB.show();
    }

    public static class DialogItem {
        public final String text;
        public final int icon;

        DialogItem(String text, Integer icon) {
            this.text = text;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}
