package com.example.weatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HelloController {

    // API get request
    void apiCall(String cityInput) {

        ApiKey ak = new ApiKey();
        String apiKey = ak.apiKey();

        try {
            // URL
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityInput + "&units=metric&lang=en&APPID=" + apiKey);

            // Connection object
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Req method --> get
            con.setRequestMethod("GET");

            // Response status code
            int status = con.getResponseCode();

            // Read data using BufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(content.toString());
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String main = weatherObject.getString("main");
            String description = weatherObject.getString("description");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            double temp = mainObject.getDouble("temp");
            int pressure = jsonObject.getJSONObject("main").getInt("pressure");
            int humidity = jsonObject.getJSONObject("main").getInt("humidity");
            int visibility = jsonObject.getInt("visibility");
            String name = jsonObject.getString("name");

            // Disconnect the connection
            con.disconnect();

            // Print the whole response content
            //System.out.println(content.toString());

            // Print the values
            System.out.println(mainObject);
            System.out.println(jsonObject);
            System.out.println(weatherObject);
            System.out.println("main: " + main);
            System.out.println("description: " + description);
            System.out.println("temp: " + temp);
            System.out.println("pressure: " + pressure);
            System.out.println("humidity: " + humidity);
            System.out.println("visibility: " + visibility);

            //Move the values to the app
            desc.setText(description.toUpperCase());
            celciustext.setText(Double.toString(temp) + " C°");
            city.setText(name);
            airpressure.setText("Pressure: " + Integer.toString(pressure) + " hPa");
            humiditypercent.setText("Humidity: " + Integer.toString(humidity) + " %");

            // Change descriptive images
            if (Objects.equals(main, "Clear")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/clear.gif")));
            } else if (Objects.equals(main, "Thunderstorm")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/thunderstorm.gif")));
            } else if (Objects.equals(main, "Drizzle")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/drizzle.gif")));
            } else if (Objects.equals(main, "Rain")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/rain.gif")));
            } else if (Objects.equals(main, "Snow")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/snow.gif")));
            } else if (Objects.equals(main, "Fog")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/fog.gif")));
            }else if (Objects.equals(main, "Clouds")) {
                backgroundimg.setImage(new Image(new FileInputStream("src/main/resources/com/example/weatherapp/clouds.gif")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ImageView backgroundimg;

    @FXML
    private Text celciustext;

    @FXML
    private Button submitButton;

    @FXML
    private Text airpressure;

    @FXML
    private Text city;

    @FXML
    private TextField citytextfield;

    @FXML
    private Text humiditypercent;

    @FXML
    private Text desc;

    @FXML
    private Text weather;

    @FXML
    void HaePressed(ActionEvent event) {
        System.out.println("Submit pressed");
        apiCall(citytextfield.getText());
        System.out.println(citytextfield.getText());
    }

}

