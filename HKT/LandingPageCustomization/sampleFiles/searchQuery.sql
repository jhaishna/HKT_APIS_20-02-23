select result.* FROM (
select 
created_t,
engAdj.STRING_ID adjCode,
engAdj.STRING english_description,
chnAdj.STRING chinese_description,
cfm.GLID glid
from
(select
mrb_seconds_to_date(s.CREATED_T) created_t, 
s.STRING_ID, 
s.DOMAIN,
s.VERSION, 
s.STRING, 
s.LOCALE
from strings_t s 
where 1=1 
and s.DOMAIN in('Reason Codes-Debit Reasons', 'Reason Codes-Credit Reasons')
and s.VERSION = 1) engAdj,
(select 
s.STRING_ID, 
s.DOMAIN, 
s.STRING,
s.VERSION, 
s.LOCALE
from strings_t s 
where 1=1 
and s.DOMAIN in('Reason Codes-Debit Reasons', 'Reason Codes-Credit Reasons')
and s.VERSION = 101) chnAdj,
config_map_glid_t cfm
where 1=1 
and engAdj.STRING_ID = chnAdj.STRING_ID
and engAdj.STRING_ID = cfm.REASON_CODE_STR_ID
and engAdj.VERSION = cfm.REASON_CODE_STR_VER
order by engAdj.STRING_ID asc) result
;