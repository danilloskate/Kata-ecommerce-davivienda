import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html'
})
export class ProductListComponent implements OnInit {
  products: any[] = [];
  cart: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get('/api/products').subscribe((data: any) => this.products = data);
  }

  addToCart(product: any) {
    const item = this.cart.find(i => i.productId === product.id);
    if (item) {
      item.quantity++;
    } else {
      this.cart.push({ productId: product.id, name: product.name, quantity: 1, price: product.price });
    }
    localStorage.setItem('cart', JSON.stringify(this.cart));
    alert('Added to cart');
  }
}
