import { Link } from "react-router-dom";

export function Register() {
    return (
        <>
            <h1>Net Banking</h1>
            <div className="flex flex-col gap-8 m-16">
                <form action="/register" method="POST" className="flex flex-col gap-4">
                    <div className="flex flex-col items-start">
                        <label htmlFor="name" className="text-xs">Name</label>
                        <input type="text" id="name" name="name" className="min-w-[250px] rounded-md h-10 p-2" />
                    </div>
                    <div className="flex flex-col items-start">
                        <label htmlFor="email" className="text-xs">Email</label>
                        <input type="text" id="email" name="email" className="min-w-[250px] rounded-md h-10 p-2" />
                    </div>
                    <div className="flex flex-col items-start">
                        <label htmlFor="password" className="text-xs">Password</label>
                        <input type="password" id="password" name="password" className="min-w-[250px] rounded-md h-10 p-2" />
                    </div>
                    <div className="flex flex-col items-start">
                        <label htmlFor="confirmPassword" className="text-xs">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" className="min-w-[250px] rounded-md h-10 p-2" />
                    </div>
                    <input type="submit" value="Register"
                        className="bg-blue-900 py-3 rounded-md text-neutral-50 hover:text-zinc-400" />
                </form>
                {/* <Link to='/login' className='bg-zinc-900 py-3 rounded-md text-neutral-50'>Login</Link> */}
                <Link to='/' className="mr-4"> &lt; Back</Link>
            </div>
        </>
    )
}
