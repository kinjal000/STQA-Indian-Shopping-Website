# STQA FINAL WORKING — ShopEase India

This is a beginner-friendly Java + Maven + Selenium practical project with a working local shopping website.

## Demo account
Username: `student`
Password: `shop123`

## Website features
- Login and invalid-login error
- 9 separate product cards with INR prices
- Add to cart and remove from cart
- Cart quantity, subtotal, delivery and total
- Checkout form and order confirmation
- Responsive layout for laptop and mobile

This is a demo store. It does not take real payments or create real deliveries.

## IMPORTANT: Open the correct folder
1. Download and extract `STQA_FINAL_WORKING.zip`.
2. In Finder, open Downloads and locate the extracted folder named `STQA_FINAL_WORKING`.
3. Open VS Code → File → Open Folder.
4. Select `STQA_FINAL_WORKING` itself — not `src`, not `java`, and not an older STQA folder.
5. Check that `pom.xml` is visible in the VS Code Explorer.

## Start the website
Open VS Code → Terminal → New Terminal. Run these commands one by one:

```bash
cd ~/Downloads/STQA_FINAL_WORKING
mvn clean compile
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass="stqa.StartWebsite"
```

If the extracted folder is not directly inside Downloads, type `cd ` (with a space), drag the `STQA_FINAL_WORKING` folder from Finder into the terminal, and press Enter. Then run the two Maven commands above.

The launcher tries port 8080. If 8080 is already occupied by an older run, it automatically uses port 8081. It prints the exact website URL and opens Chrome automatically.

Keep the terminal open while using the website. Stop it with Ctrl+C.

## Selenium test commands
Run these from the same project folder. Each test opens Chrome and keeps it open until you press Enter in the terminal.

Valid login:
```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass="stqa.ValidLoginTest"
```

Invalid login:
```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass="stqa.InvalidLoginTest"
```

Product cart:
```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass="stqa.ProductCartTest"
```

## If Maven says BUILD FAILURE
- Check the terminal folder name with `pwd`.
- Check that `pom.xml` appears with `ls`.
- If it does not, you are in the wrong folder. Use `cd ~/Downloads/STQA_FINAL_WORKING` (or drag the extracted folder into Terminal).
- Run `mvn clean compile` again.

## Expected login flow
Starting the website opens the login page. Correct demo credentials take you to the product listing. The cart and checkout pages work through the website navigation.


