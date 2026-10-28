```
 autorizacion  : 'C' con autorización / 'S' sin autorización
 ```

# Cómo generar el secreto JWT

El secreto firma los tokens de login. Debe ser aleatorio, de mínimo 32 caracteres
y nunca debe subirse al repositorio.

## 1. Generarlo
PowerShell (Windows):

```
$b = New-Object byte[] 48
$rng = [Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($b)
$rng.Dispose()
[Convert]::ToBase64String($b)
```

## 2. Configurarlo

```properties
app.security.jwt.secret=${JWT_SECRET}
app.security.jwt.expiracion-minutos=60
```