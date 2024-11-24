import { Component } from '@angular/core';
import { SlipService } from '../../services/slip.service';

@Component({
  selector: 'app-slip-loader',
  templateUrl: './slip-loader.component.html',
  styleUrls: ['./slip-loader.component.scss'],
})
export class SlipLoaderComponent {
  quantidade: number = 1;
  slips: any[] = [];
  loading: boolean = false;

  constructor(private slipService: SlipService) {}

  /**
   * Chama o serviço para carregar uma quantidade específica de slips.
   */
  carregarSlips() {
    this.loading = true;
    this.slipService.carregaSlips(this.quantidade).subscribe(
      (data: any[]) => {
        this.slips = data;
        this.loading = false;
      },
      (error: any) => {
        console.error('Erro ao carregar slips:', error);
        this.loading = false;
      }
    );
  }

  /**
   * Chama o serviço para obter todos os slips disponíveis.
   */


  carregarTodosSlips() {
    this.loading = true;
    this.slipService.getTodosSlips().subscribe(
      (data: any[]) => {
        this.slips = data;
        this.loading = false;
      },
      (error: any) => {
        console.error('Erro ao carregar todos os slips:', error);
        this.loading = false;
      }
    );
  }
}
