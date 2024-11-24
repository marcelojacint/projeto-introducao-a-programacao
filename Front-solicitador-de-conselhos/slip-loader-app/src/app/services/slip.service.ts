import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SlipService {
  private baseUrl = 'http://localhost:8080/api/slips';  // URL base da API

  constructor(private http: HttpClient) {}

  // Método para carregar uma quantidade específica de conselhos
  carregaSlips(quantidade: number): Observable<any[]> {
    // Verifica se a quantidade é válida
    if (quantidade < 1) {
      return new Observable<any[]>((observer) => {
        observer.error('Quantidade inválida, deve ser maior que 0');
      });
    }

    // Faz a requisição HTTP para carregar os slips
    return this.http.get<any[]>(`${this.baseUrl}/carregarSlips/${quantidade}`).pipe(
      catchError((error) => {
        console.error('Erro ao carregar slips:', error);
        return throwError(() => new Error('Erro ao carregar slips'));
      })
    );
  }

  // Método para carregar todos os slips
  getTodosSlips(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`).pipe(
      catchError((error) => {
        console.error('Erro ao carregar todos os slips:', error);
        return throwError(() => new Error('Erro ao carregar todos os slips'));
      })
    );
  }
}
