const API_URL = import.meta.env.VITE_API_URL

export type PaymentMethod = 'CREDIT_CARD' | 'PIX'

export interface PaymentResponse {
    id: string
    reservation: {
        id: string
    }
    method: PaymentMethod
    status: string
    amount: number
    transactionId?: string
    processedAt?: string
}

export async function createPayment(
    reservationId: string,
    method: PaymentMethod
): Promise<PaymentResponse> {
    const token = localStorage.getItem('ticketpass_token')

    if (!token) {
        throw new Error('Usuário não autenticado')
    }

    const response = await fetch(`${API_URL}/api/payments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
            reservationId,
            method,
        }),
    })

    if (!response.ok) {
        throw new Error('Não foi possível criar o pagamento')
    }

    return response.json()
}

export async function approvePayment(
    paymentId: string
): Promise<PaymentResponse> {
    const token = localStorage.getItem('ticketpass_token')

    if (!token) {
        throw new Error('Usuário não autenticado')
    }

    const response = await fetch(
        `${API_URL}/api/payments/${paymentId}/approve`,
        {
            method: 'PUT',
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }
    )

    if (!response.ok) {
        throw new Error('Não foi possível aprovar o pagamento')
    }

    return response.json()
}