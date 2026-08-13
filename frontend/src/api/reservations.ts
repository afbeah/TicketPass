import { jwtDecode } from 'jwt-decode'

const API_URL = import.meta.env.VITE_API_URL

interface JwtPayload {
    userId: string
    role: string
}

interface ReservationResponse {
    id: string
    customerId: string
    ticketId: string
    status: string
}

export async function createReservation(
    ticketId: string
): Promise<ReservationResponse> {
    const token = localStorage.getItem('ticketpass_token')

    if (!token) {
        throw new Error('Usuário não autenticado')
    }

    const payload = jwtDecode<JwtPayload>(token)

    if (!payload.userId) {
        throw new Error('Usuário inválido')
    }

    const response = await fetch(`${API_URL}/api/reservations`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
            customerId: payload.userId,
            ticketId,
        }),
    })

    if (!response.ok) {
        throw new Error('Não foi possível reservar o ingresso')
    }

    return response.json()
}