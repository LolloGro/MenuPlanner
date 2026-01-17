export async function isLoggedIn(): Promise<boolean> {
   try{
       const res = await fetch("/api/auth",{

           method: "GET",
           credentials: "include",
           headers: {
               "Accept": "application/json",
           },
       });

       return res.ok;
   }catch{
       return false;
   }
}
