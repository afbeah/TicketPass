import { useState } from 'react'
import { validateTicket } from '../api/gate'
import type { GateValidationStatus } from '../types/GateValidation'

interface GateProps {
    onBack: () => void
}

function Gate({ onBack }: GateProps) {
    const [qrCode, setQrCode] = useState('')
    const [eventId, setEventId] = useState('')

    const [status, setStatus] =
        useState<GateValidationStatus | null>(null)

    const [message, setMessage] = useState('')
    const [loading, setLoading] = useState(false)

    async function handleValidate() {
        if (!qrCode.trim() || !eventId.trim()) {
            setStatus('INVALID')
            setMessage('Informe o código do ingresso e o evento.')
            return
        }

        try {
            setLoading(true)
            setStatus(null)
            setMessage('')

            const result = await validateTicket(
                qrCode.trim(),
                eventId.trim()
            )

            setStatus(result.status)
            setMessage(result.message)
        } catch {
            setStatus('INVALID')
            setMessage(
                'Não foi possível validar o ingresso.'
            )
        } finally {
            setLoading(false)
        }
    }

    return (
        <main className="gate-page">
            <section className="gate-card">
                <button
                    className="gate-back"
                    onClick={onBack}
                >
                    ← Voltar
                </button>

                <span className="eyebrow">
          PORTARIA
        </span>

                <h2>Validar ingresso</h2>

                <p className="gate-description">
                    Informe o código do ingresso para verificar
                    se a entrada está autorizada.
                </p>

                <div className="gate-form">
                    <label>
                        Evento
                        <input
                            type="text"
                            placeholder="ID do evento"
                            value={eventId}
                            onChange={(event) =>
                                setEventId(event.target.value)
                            }
                        />
                    </label>

                    <label>
                        Código do ingresso
                        <input
                            type="text"
                            placeholder="Digite ou cole o código"
                            value={qrCode}
                            onChange={(event) =>
                                setQrCode(event.target.value)
                            }
                        />
                    </label>

                    <button
                        className="gate-validate-button"
                        onClick={handleValidate}
                        disabled={loading}
                    >
                        {loading
                            ? 'Validando...'
                            : 'Validar ingresso'}
                    </button>
                </div>

                {status && (
                    <div
                        className={`gate-result gate-result-${status.toLowerCase()}`}
                    >
                        <strong>{status}</strong>

                        <p>{message}</p>
                    </div>
                )}
            </section>
        </main>
    )
}

export default Gate