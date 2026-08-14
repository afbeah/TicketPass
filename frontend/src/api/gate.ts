import type { GateValidationResponse } from '../types/GateValidation'

export async function validateTicket(
    qrCode: string,
    eventId: string
): Promise<GateValidationResponse> {
    const token = localStorage.getItem('ticketpass_token')

    const response = await fetch(
        'http://localhost:8080/api/gate/validate',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                qrCode,
                eventId,
            }),
        }
    )

    if (!response.ok) {
        throw new Error('Não foi possível validar o ingresso.')
    }

    return response.json()
}