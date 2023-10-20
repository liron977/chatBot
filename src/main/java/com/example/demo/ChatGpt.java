package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGpt {
    //private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ChatGpt.class);

    private String apiKey;
    private String endpoint;
    private  String model;
    private  String url;
    ChatGpt(String endPoint, String chatGptToken){

        this.apiKey =chatGptToken;
        this.endpoint = endPoint;
        this.model = "gpt-3.5-turbo";
        this.url = "https://api.openai.com/v1/chat/completions";
        //this.url = "https://chatgpt-api.shn.hk/v1/";


    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
    /* public String getResponeFromChatGpt(String input){
         String response = sendQuery(input);
         System.out.println(response);
         return response;
     }

    public  String sendQuery(String input) {
         // Build input and API key params
         JSONObject payload = new JSONObject();
         JSONObject message = new JSONObject();
         JSONArray messageList = new JSONArray();

         message.put("role", "user");
         message.put("content", input);
         messageList.put(message);

         payload.put("model", "gpt-3.5-turbo"); // model is important
         payload.put("messages", messageList);
         payload.put("temperature", 0.7);

         StringEntity inputEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);

         // Build POST request
         HttpPost post = new HttpPost(endpoint);
         post.setEntity(inputEntity);
         post.setHeader("Authorization", "Bearer " + apiKey);
         post.setHeader("Content-Type", "application/json");

         // Send POST request and parse response
         try (CloseableHttpClient httpClient = HttpClients.createDefault();
              CloseableHttpResponse response = httpClient.execute(post)) {
             HttpEntity resEntity = response.getEntity();

           //  resEntity.getContent().reset();
             InputStream inputStream = resEntity.getContent();
             StringBuilder stringBuilder = new StringBuilder();

             try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                 String line;
                 while ((line = reader.readLine()) != null) {
                     stringBuilder.append(line);
                 }
             }

             String resJsonString = stringBuilder.toString();
             JSONObject resJson = new JSONObject(resJsonString);

 // Check if the response contains an "error" field
             if (resJson.has("error")) {
                 // Handle the error case
                 JSONObject errorObject = resJson.getJSONObject("error");
                 String errorType = errorObject.getString("type");
                 String errorDetails = errorObject.getString("message");
                 return "Error Type: " + errorType + ", Error Message: " + errorDetails;
             }

 // If no error, proceed to parse the response
             JSONArray responseArray = resJson.getJSONArray("choices");
             List<String> responseList = new ArrayList<>();

             for (int i = 0; i < responseArray.length(); i++) {
                 JSONObject responseObj = responseArray.getJSONObject(i);
                 String responseString = responseObj.getJSONObject("message").getString("content");
                 responseList.add(responseString);
             }

 // Convert response list to JSON and return it
             Gson gson = new Gson();
             String jsonResponse = gson.toJson(responseList);
             return jsonResponse;

         } catch (IOException | JSONException e) {
            // LOGGER.info("Error sending request: {}"+e.getMessage());
             return "Error: " + e.getMessage();
         }
    }

     */
    public String chatGPT(String prompt) {
        int maxRetries = 3; // Maximum number of retries
        int retryDelay = 1000; // Initial retry delay in milliseconds
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json");

                // The request body
                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();

                // Check the HTTP response code
                int responseCode = connection.getResponseCode();
                if (responseCode == 429) {
                    // Rate limit exceeded, back off and retry
                    int retryAfter = Integer.parseInt(connection.getHeaderField("Retry-After"));
                    retryCount++;
                    Thread.sleep(retryAfter * 1000); // Sleep for the specified duration
                    continue; // Retry the request
                }

                // Response from ChatGPT
                if (responseCode == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuffer response = new StringBuffer();

                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();

                    // Calls the method to extract the message.
                    return extractMessageFromJSONResponse(response.toString());
                } else {
                    throw new RuntimeException("Unexpected response code: " + responseCode);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // If all retries fail, return an error message
        return "Error: Maximum retries exceeded.";
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

}
