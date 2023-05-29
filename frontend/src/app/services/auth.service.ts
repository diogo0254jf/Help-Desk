import { Injectable } from "@angular/core";
import { Credenciais } from "../models/credenciais";
import { HttpClient } from "@angular/common/http";
import { APICONFIG } from "../config/api.config";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) {}

  authenticate(creds: Credenciais) {
    return this.http.post(`${APICONFIG.baseUrl}/login`, creds, {
      observe: "response",
      responseType: "text",
    });
  }
}
