const products = [
 {id:1,name:"Classic Backpack",category:"BAGS",price:1299,emoji:"🎒",desc:"A roomy everyday backpack for college, work and short trips."},
 {id:2,name:"Wireless Headphones",category:"ELECTRONICS",price:1899,emoji:"🎧",desc:"Comfortable headphones with clear sound for your daily playlist."},
 {id:3,name:"Stainless Steel Bottle",category:"LIFESTYLE",price:499,emoji:"🥤",desc:"Reusable insulated bottle to keep your drink close all day."},
 {id:4,name:"Everyday Sneakers",category:"FOOTWEAR",price:2499,emoji:"👟",desc:"Lightweight sneakers made for everyday comfort."},
 {id:5,name:"Smart Watch",category:"ELECTRONICS",price:3299,emoji:"⌚",desc:"A clean everyday watch with useful activity features."},
 {id:6,name:"Desk Lamp",category:"HOME",price:899,emoji:"💡",desc:"A simple adjustable lamp for your study table."},
 {id:7,name:"Travel Coffee Mug",category:"LIFESTYLE",price:599,emoji:"☕",desc:"A handy mug for coffee on the way to class or work."},
 {id:8,name:"Minimal Wallet",category:"ACCESSORIES",price:749,emoji:"👛",desc:"A compact wallet with space for your everyday essentials."},
 {id:9,name:"Yoga Mat",category:"FITNESS",price:999,emoji:"🧘",desc:"A comfortable mat for stretching and home workouts."}
];
let cart = [];
let currentUser = "";
const money = n => "₹" + n.toLocaleString("en-IN");
function el(id){return document.getElementById(id)}
function showPage(page){
 ["loginPage","productsPage","cartPage","checkoutPage","completePage"].forEach(id=>el(id).hidden=true);
 const map={login:"loginPage",products:"productsPage",cart:"cartPage",checkout:"checkoutPage",complete:"completePage"};
 el(map[page]).hidden=false;
 el("cartTop").hidden = page==="login" || page==="complete";
 el("logoutBtn").hidden = !currentUser || page==="login";
 el("welcomeText").textContent=currentUser ? "Hi, "+currentUser : "Welcome";
 if(page==="products") renderProducts();
 if(page==="cart") renderCart();
 if(page==="checkout") renderCheckout();
 window.scrollTo(0,0);
}
function login(){
 const u=el("username").value.trim(), p=el("password").value;
 if(u==="student" && p==="shop123"){currentUser=u;el("loginError").textContent="";showPage("products")}
 else{el("loginError").textContent="Username or password is incorrect. Please try again."}
}
function logout(){currentUser="";cart=[];el("username").value="";el("password").value="";updateCount();showPage("login")}
function renderProducts(){
 el("productGrid").innerHTML=products.map(p=>`<article class="product-card" data-product-id="${p.id}">
 <div class="product-art">${p.emoji}</div><div class="product-info"><span class="category">${p.category}</span>
 <h3>${p.name}</h3><p>${p.desc}</p><div class="product-bottom"><span class="price">${money(p.price)}</span>
 <button class="small-btn" data-test="add-${p.id}" onclick="addToCart(${p.id})">Add to cart</button></div></div></article>`).join("");
}
function addToCart(id){const item=cart.find(x=>x.id===id);if(item)item.qty++;else cart.push({id,qty:1});updateCount();const b=document.querySelector(`[data-test="add-${id}"]`);if(b){b.textContent="Added ✓";setTimeout(()=>{if(b.isConnected)b.textContent="Add to cart"},900)}}
function removeFromCart(id){cart=cart.filter(x=>x.id!==id);updateCount();renderCart()}
function updateCount(){el("cartCount").textContent=cart.reduce((n,x)=>n+x.qty,0)}
function totals(){const subtotal=cart.reduce((n,x)=>n+products.find(p=>p.id===x.id).price*x.qty,0);const shipping=subtotal===0||subtotal>=999?0:49;return {subtotal,shipping,total:subtotal+shipping}}
function renderCart(){
 if(cart.length===0){el("cartContent").innerHTML=`<div class="empty"><div class="big">🛒</div><h2>Your cart is empty</h2><p class="muted">Add a few products and they will appear here.</p><button class="primary" onclick="showPage('products')">Browse products</button></div>`;return}
 const t=totals();
 el("cartContent").innerHTML=`<div class="cart-layout"><div class="cart-list">${cart.map(x=>{const p=products.find(p=>p.id===x.id);return `<div class="cart-row" data-cart-id="${p.id}"><div class="cart-emoji">${p.emoji}</div><div><h3>${p.name}</h3><p>Quantity: ${x.qty} · ${money(p.price)} each</p></div><b>${money(p.price*x.qty)}</b><button class="remove-btn" data-test="remove-${p.id}" onclick="removeFromCart(${p.id})">Remove</button></div>`}).join("")}<button class="continue" onclick="showPage('products')">← Continue shopping</button></div>${summaryHTML(t,true)}</div>`;
}
function summaryHTML(t,button){return `<aside class="summary-card"><h2>Price details</h2><div class="sum-line"><span>Subtotal</span><b>${money(t.subtotal)}</b></div><div class="sum-line"><span>Delivery</span><b>${t.shipping===0?"FREE":money(t.shipping)}</b></div><div class="sum-line sum-total"><span>Total</span><b>${money(t.total)}</b></div>${button?'<button class="primary" data-test="checkout" onclick="showPage(\'checkout\')">Proceed to checkout</button>':""}</aside>`}
function renderCheckout(){if(cart.length===0){showPage("cart");return}el("checkoutSummary").innerHTML=summaryHTML(totals(),false)}
el("checkoutForm").addEventListener("submit",e=>{e.preventDefault();if(!el("checkoutForm").reportValidity())return;const order="SE"+Math.floor(100000+Math.random()*900000);el("orderNumber").textContent="Order number: "+order;cart=[];updateCount();showPage("complete")});
function startShopping(){showPage("products")}
showPage("login");
