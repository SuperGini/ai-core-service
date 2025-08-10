import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AiResponse} from '../carriers/ai.response';

@Injectable({providedIn: 'root'})
export class RagService {


    protected httpClient = inject(HttpClient);


    getAiResponse(question: string): Observable<AiResponse> {

        const questionRequest: UserRequest = {
            message: question
        }

        return this.httpClient
            .post<AiResponse>('http://localhost:8080/rag', questionRequest);
    }

    loadPdf(files: File[]) {
        const formData = new FormData();
        formData.append(`file`, files[0])

        return this.httpClient.post('http://localhost:8080/load2', formData);

    }


}
