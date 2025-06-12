import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent {
  cart: any[] = [];

  constructor(private http: HttpClient) {
    const saved = localStorage.getItem('cart');
    if (saved) this.cart = JSON.parse(saved);
  }

  getTotal(): number {
    return this.cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  checkout() {
    const userId = 'mock-user-id'; // Replace with real user ID from backend
    this.http.post('/api/orders', {
      userId,
      items: this.cart.map(i => ({ productId: i.productId, quantity: i.quantity }))
    }).subscribe({
      next: () => {
        alert('Order placed successfully');
        localStorage.removeItem('cart');
        this.cart = [];
      },
      error: () => alert('Checkout failed')
    });
  }
}
