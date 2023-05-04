import { Form, Link, redirect, useActionData } from "react-router-dom";

export async function loginAction({ request }) {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    let response = await fetch('/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });

    if (response.status == 400) {
        // Show the error on the form
        return await response.json();
    } else if (response.status != 200) {
        // Show the error page
        let errorMessage = await response.json();
        if (errorMessage.error == 'Unauthorized') {
            // throw Error(errorMessage.error);
            return { error: errorMessage.error };
        }
    }

    let resp = await response.json();
    localStorage.setItem('token', resp.token);
    return redirect(`/login-redirect`);
}

export function Login() {
    const errors = useActionData();
    const errorMessage = errors?.error;
    return (
        <>
            <h1>Net Banking</h1>
            <div className="flex flex-col gap-8 m-16">
                <p className="text-red-500">{errorMessage}</p>
                <Form action="/login" method="POST" className="flex flex-col gap-4">
                    <div className="flex flex-col items-start">
                        <label htmlFor="email" className="text-xs">Email</label>
                        <input type="text" id="email" name="email" className="min-w-[250px] rounded-md h-10 p-2" />
                    </div>
                    <div className="flex flex-col items-start">
                        <label htmlFor="password" className="text-xs">Password</label>
                        <input type="password" id="password" name="password" className="min-w-[250px] rounded-md h-10 p-2" />
                    </div>
                    <input type="submit" value="Login"
                        className="bg-blue-900 py-3 rounded-md text-neutral-50 hover:text-zinc-400" />
                </Form>
                <Link to='/' className="mr-4"> &lt; Back</Link>
            </div>
        </>
    )
}