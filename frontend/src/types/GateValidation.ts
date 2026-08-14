export type GateValidationStatus =
    | 'VALID'
    | 'INVALID'
    | 'ALREADY_USED'
    | 'WRONG_EVENT'

export interface GateValidationResponse {
    status: GateValidationStatus
    message: string
}