package com.example.android.justjava2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava2.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void incrementOrder(View view) {
        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "The maximum number of orders is 100";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        // Log.v("MainActivity", "Has wipped cream:" + hasWhippedCream);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText getEditableText = (EditText) findViewById(R.id.nameTextView);
        String getName = getEditableText.getText().toString();
        //Log.v("MainActivity", "Name: " + getName);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, getName);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for" + getName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, getName));


    }


    /**
     * Calculate price order
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate    is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;

        if (hasWhippedCream && hasChocolate) {
            return (price + 3) * quantity;
        } else if (hasChocolate) {
            return (price + 2) * quantity;
        } else if (hasWhippedCream) {
            return (price + 1) * quantity;
        } else {
            return quantity * price;
        }
    }

    /**
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate    is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean hasChocolate, String getName) {

        String priceMessage = getString(R.string.order_summary_name, getName);
        priceMessage += "\n" + getString(R.string.addWhippedCream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.addChocolate) + hasChocolate;
        priceMessage += "\n" + getString(R.string.Quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thank_you);


        return priceMessage;

    }

    /*
     * This method decrement the quantity order
     */


    public void decrement(View view) {


        if (quantity == 1) {

            Context context = getApplicationContext();
            CharSequence text = "You need at least 1 cup of coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffes) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffes);
    }

    /**
     * /*
     * This method displays the given text on the screen.
     */
    // private void displayMessage(String message) {
    //   TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
    //   orderSummaryTextView.setText(message);
    //}

}