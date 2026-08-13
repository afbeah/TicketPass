import type { LoginRequest, LoginResponse } from '../types/Auth'

const API_URL = import.meta.env.VITE_API_URL

export async function login(
    request: LoginRequest
): Promise<LoginResponse> {
    const response = await fetch(`${API_URL}/api/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(request),
    })

    if (!response.ok) {
        throw new Error('E-mail ou senha inválidos')
    }

    return response.json()
}