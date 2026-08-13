import { useState } from 'react'
import { login } from '../api/auth'

interface LoginProps {
    onLoginSuccess: (token: string) => void
}

function Login({ onLoginSuccess }: LoginProps) {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')

    async function handleSubmit(event: React.FormEvent) {
        event.preventDefault()

        try {
            setLoading(true)
            setError('')

            const response = await login({
                email,
                password,
            })

            localStorage.setItem('ticketpass_token', response.token)

            onLoginSuccess(response.token)
        } catch {
            setError('E-mail ou senha inválidos.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <main className="login-page">
            <div className="login-card">
                <span className="eyebrow">TICKETPASS</span>

                <h2>Bem-vindo de volta.</h2>

                <p>
                    Entre na sua conta para reservar seus ingressos.
                </p>

                <form onSubmit={handleSubmit}>
                    <label>
                        E-mail
                        <input
                            type="email"
                            value={email}
                            onChange={(event) => setEmail(event.target.value)}
                            placeholder="seu@email.com"
                            required
                        />
                    </label>

                    <label>
                        Senha
                        <input
                            type="password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            placeholder="Sua senha"
                            required
                        />
                    </label>

                    {error && <p className="error">{error}</p>}

                    <button type="submit" disabled={loading}>
                        {loading ? 'Entrando...' : 'Entrar'}
                    </button>
                </form>
            </div>
        </main>
    )
}

export default Login